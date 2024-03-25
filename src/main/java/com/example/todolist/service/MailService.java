package com.example.todolist.service;

import com.example.todolist.domain.EmailKey;
import com.example.todolist.domain.Member;
import com.example.todolist.dto.request.CheckEmailKeyReqDto;
import com.example.todolist.dto.request.FindPasswordReqDto;
import com.example.todolist.dto.request.SendEmailReqDto;
import com.example.todolist.exception.CustomException;
import com.example.todolist.exception.ErrorCode;
import com.example.todolist.repository.EmailKeyRepository;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MailService {
    private final MemberRepository memberRepository;
    private final EmailKeyRepository emailKeyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Transactional(readOnly = true)
    public String findId(SendEmailReqDto sendEmailReqDto){
        Member member=memberRepository.findByEmail(sendEmailReqDto.getEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        sendEmail(sendEmailReqDto.getEmail(),"안녕하세요, todoList 아이디 찾기 관련 이메일입니다.",
                member.getNickname()+"님의 아이디는 "+member.getLoginId()+"입니다.");
        return member.getLoginId();
    }

    //비밀번호를 찾는게 아닌 새로 발급하는 이유는 암호화된 비밀번호를 db에 저장했기때문에 보안상 이유도 있고 다시 복호화하기 어렵기때문이다.
    public String findPassword(FindPasswordReqDto findPasswordReqDto){
        Member member=memberRepository.findByEmail(findPasswordReqDto.getEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));
        if(!member.getLoginId().equals(findPasswordReqDto.getLoginId()))
            throw new CustomException(ErrorCode.INVALID_LOGIN_ID);

        String newPassword=createRandomKey();
        updatePassword(newPassword,member);

        sendEmail(findPasswordReqDto.getEmail(),"안녕하세요, todoList 임시 비밀번호 발급 관련 이메일입니다.",
                member.getNickname()+"님의 임시비밀번호는 "+newPassword+"입니다. 비밀번호 변경을 통해 비밀번호를 바꿔주세요.");
        return newPassword;

    }

    //전송한 키와 작성한 키가 맞는지 검사한다.
    public String checkEmailKeyEqual(CheckEmailKeyReqDto checkEmailKeyReqDto){
        if(!emailKeyRepository.existsById(checkEmailKeyReqDto.getKey()))
            throw new CustomException(ErrorCode.INVALID_EMAIL_KEY);

        return "ok";
    }

    //회원가입 시 이메일 인증을 위한 키를 전송한다.
    public String sendEmailKey(SendEmailReqDto sendEmailReqDto){
        String email=sendEmailReqDto.getEmail();
        String key=createRandomKey();

        //재전송 시 하나의 키만을 남겨놓기 위함이다.
        emailKeyRepository.deleteAll();

        emailKeyRepository.save(new EmailKey(key));
        sendEmail(email,"안녕하세요, todoList 이메일 인증 키 발급 관련 이메일입니다.",
                "이메일 인증키는 "+ key +"입니다. 5분 안에 인증해주세요.");

        return key;

    }

    //toAddress에 발신자를 작성하고,subject에 이메일 제목을, text에 본문 내용을 작성하면 된다.
    public void sendEmail(String toAddress,String subject, String text){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setFrom(fromAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }

    // 8자리 영어,숫자,특수문자를 포함한 키를 생성하는 메소드
    public String createRandomKey(){
        /*
            IntStream.concat()은 두개의 정수형 스트림을 하나로 결합한다.
            48~57=숫자, 65~90=대문자, 97~122=소문자, "!@#$%^&_=+".chars()는 특수문자들을 아스키코드로 변환, 즉 정수로 변환
            mapToObj(i -> String.valueOf((char) i))는 IntStream의 각 요소, 아스키코드 값을 해당 문자로 변환후 문자열로 변환한다.
            collect(Collectors.joining())는 스트림의 요소들을 하나의 문자열로 합친다.
            즉, 숫자,영어,특수문자를 가진 문자열을 만들어낸다.
         */
        String randomKey=IntStream.concat(
                IntStream.concat(IntStream.rangeClosed(48, 57), IntStream.rangeClosed(65, 90)),
                IntStream.concat(IntStream.rangeClosed(97, 122),"!@#$%^&_=+".chars()))
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());

        //숫자,영어,특수문자가 꼭 포함되어야하므로 세개를 넣어 준뒤 랜덤하게 넣어준다.
        String randomNum=IntStream.rangeClosed(48,57)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
        String randomEng=IntStream.concat(IntStream.rangeClosed(65,90),IntStream.rangeClosed(97,122))
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
        String randomSymbol="!@#$%^&_=+".chars()
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());

        StringBuilder str=new StringBuilder();
        SecureRandom secureRandom=new SecureRandom();

        str.append(randomNum.charAt(secureRandom.nextInt(randomNum.length())));
        str.append(randomEng.charAt(secureRandom.nextInt(randomEng.length())));
        str.append(randomSymbol.charAt(secureRandom.nextInt(randomSymbol.length())));

        for(int i=0;i<5;i++){
            str.append(randomKey.charAt(secureRandom.nextInt(randomKey.length())));
        }

        return str.toString();
    }

    public void updatePassword(String str, Member member){
        member.changePassword(str);
        member.passwordEncode(passwordEncoder);
    }
}
