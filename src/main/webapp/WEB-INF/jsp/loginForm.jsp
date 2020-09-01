<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" id="testpage">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>OP.GG 로그인</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <style>
      #root {
        position: relative;
        min-height: 100%;
        background-color: #f3f5f7;
      }

      div {
        margin: 0;
        padding: 0;
        border: 0;
        display: block;
        font-size: 100%;
        font-family: Helvetica, Helvetica Neue, Arial, Malgun Gothic,
          AppleSDGothicNeo-Regular, Dotum, AppleSDGothic, Apple SD Gothic Neo,
          AppleGothic, sans-serif;
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 100%;
        font-family: Helvetica, Helvetica Neue, Arial, Malgun Gothic,
          AppleSDGothicNeo-Regular, Dotum, AppleSDGothic, Apple SD Gothic Neo,
          AppleGothic, sans-serif;
        word-break: keep-all;
      }
      .member-card-layout {
        position: relative;
        min-width: 320px;
        min-height: 100%;
        padding: 170px 0 200px;
      }

      .member-card-layout__container {
        position: relative;
        margin: 0 auto;
        width: 450px;
        min-height: 682px;
        background-color: #fff;
        box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
      }

      .member-card-layout__inner {
        margin: 0 40px;
        padding-bottom: 40px;
      }
      /* OPGG 로고 */
      .member-card-layout__logo {
        padding-top: 48px;
        text-align: center;
        margin-bottom: 42px;
      }
      h1 {
        display: block;
        font-size: 2em;
        margin-block-start: 0.67em;
        margin-block-end: 0.67em;
        margin-inline-start: 0px;
        margin-inline-end: 0px;
        font-weight: bold;
      }
      /* 로고 이미지 크기 */
      .member-card-layout__logo-image {
        width: 200px;
        height: 50px;
      }
 


    
    

      /* 이메일 로그인 text */
      .login__email-title {
        margin-top: 42px;
        font-weight: 400;
        font-size: 16px;
        line-height: 19px;
      }
      /* 이메일, 비밀번호 인풋박스 밑줄 */
      .member-input__state {
        margin-top: 27px;
        position: relative;
        padding: 10px 0 11px;
        border-bottom: 1px solid #dddfe4;
        margin-bottom: 10px;
      }

      /* 이메일 주소, 로그인 인풋박스 밑줄 */

      input {
        text-rendering: auto;
        color: -internal-light-dark(black, white);
        letter-spacing: normal;
        word-spacing: normal;
        text-transform: none;
        text-indent: 0px;
        text-shadow: none;
        display: inline-block;
        -webkit-rtl-ordering: logical;
        text-align: start;
        appearance: textfield;
        cursor: text;
        margin: 0em;
        font: 400 13.3333px Arial;
      }

      /* 인풋박스 테두리 없애기 */
      .member-input__box {
        position: relative;
        z-index: 10;
        border: 0;
        padding: 0;
        background-color: transparent;
        outline: 0;
      }

      .login__l:after {
        content: "";
        display: block;
        clear: both;
      }


      /* 버튼 글씨 색 */
      .member-button,
      .member-button:disabled {
        font-size: 16px;
        border-radius: 3px;
        display: inline-block;
        text-decoration: none;

        color: #fff;
        border: 0;
        height: 56px;
      }
      /* 로그인 버튼 */
      .login__btn {
        margin-top: 40px;
        width: 100%;
        background-color: #1ea1f7;
      }

   
    </style>
  </head>
  <body>
    <!--제일큰 컨테이너 div박스-->
    <div id="root">
      <div class="app">
        <div class="member-card-layout">
          <!-- 흰색 박스 -->
          <div class="member-card-layout__container">
            <!-- 컨테이너 안 div -->
            <div class="member-card-layout__inner">
              <!-- 로고 div -->
              <h1 class="member-card-layout__logo">
                <img
                  class="member-card-layout__logo-image"
                  src="https://member.op.gg/img_opgglogo.1924961d.svg"
                  alt="OP.GG"
                />
              </h1>
              <div class="login">
                <form id="form">
                  <h2 class="login__email-title"> 관리자 로그인</h2>
                  <div class="member-input">
                    <div class="member-input__state">
                      <!-- 이메일 input 박스 -->
                      <input
                        id="memberInput6908"
                        class="member-input__box"
                        type="text"
                        autocomplete="off"
                        name="email"
                        value=""
                        placeholder="ID"
                      /><span class="member-input__valid-wrapper"></span>
                    </div>
                  </div>

                  <div class="member-input">
                    <div class="member-input__state">
                      <!-- 비밀번호 input 박스 -->
                      <input
                        id="memberInput3108"
                        class="member-input__box"
                        type="password"
                        autocomplete="off"
                        name="password"
                        value=""
                        placeholder="비밀번호"
                      /><span class="member-input__valid-wrapper"></span>
                    </div>
                    <!-- 로그인 상태유지 checkbox -->
                  </div>
                  <div class="login__l">
                 
                  </div>
                  <button type="button" onclick="login()" class="member-button login__btn">
                    로그인
                  </button>
                  
                </form>
              </div>
            </div>
            <div class="select-language select-language--login-container"></div>
          </div>
        </div>
      </div>
    </div>
    
    <script type="text/javascript">
    
    function login(){
    	
    	let data = $("#form").serialize();
    	
    	let data1 ={
    			email: $("#memberInput6908").val(),
    			password: $("#memberInput3108").val()
    	}
    	console.log(data1);

    	$.ajax({
    		
    		type: "post",
    		url: "/jwt/common",
    		
    		contentType: "application/json; charset=utf-8",
    		data: JSON.stringify(data1),
    		dataType : "json"
    		
    	}).done(function(resp) {
    		
    	
    		location.href = "/admin/user";
    		
    		
    	}).fail(function(error) {
    		
    	});
    }
    </script>
    
  </body>
</html>
