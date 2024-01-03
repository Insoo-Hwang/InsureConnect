package com.example.InsureConnect.Api;

import com.example.InsureConnect.Service.KakaoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/kakao/callback")
    public String callback(@RequestParam(value = "code",required = false) String code
            , HttpSession session) throws Exception {
        String accessToken = kakaoService.getKakaoInfo(code);
        String view = kakaoService.getUserInfoWithToken(accessToken, session);

        return view;
    }

    @GetMapping("/kakao/logout")
    public String logout(HttpSession session){
        String view = kakaoService.logout((String)session.getAttribute("accessToken"), session);
        return view;
    }
}
