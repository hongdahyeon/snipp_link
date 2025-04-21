var joinJS = {

    checkUserId: false,
    checkUserEmail: false,

    passwordInput: $("#password"),
    passwordConfirmInput: $("#passwordConfirm"),

    userIdInput: $("#userId"),
    userEmailInput: $("#userEmail"),
    userIdCheckBtn: $("#userId-check"),
    userEmailCheckBtn: $("#userEmail-check"),

    duplicateUserIdCheck: function () {
        const userId = joinJS.userIdInput.val()
        if(userId.length === 0) {
            alert("아이디는 필수 입력값입니다.")
            return false;
        }
        Http.get(`/snipp/api/user/id/duplicate-check`, {value: userId}).then((res) => {
            alert(res.message)
            if(res['checkCanUse']) {
                joinJS.userIdInput.removeClass("is-invalid");
                joinJS.userIdInput.attr('readonly', true).attr('disabled', true);
                joinJS.userIdCheckBtn.attr('disabled', true);
                joinJS.checkUserId = true;
            }
        })
    },

    duplicateUserEmailCheck: function () {
        const userEmail = joinJS.userEmailInput.val()
        if(userEmail.length === 0) {
            alert("이메일은 필수 입력값입니다.")
            return false;
        }
        Http.get(`/snipp/api/user/email/duplicate-check`, {value: userEmail}).then((res) => {
            alert(res.message)
            if(res['checkCanUse']) {
                joinJS.userEmailInput.removeClass("is-invalid");
                joinJS.userEmailInput.attr('readonly', true).attr('disabled', true);
                joinJS.userEmailCheckBtn.attr('disabled', true);
                joinJS.checkUserEmail = true;
            }
        })
    },

    validatePassword: function () {
        const passwordVal = joinJS.passwordInput.val()
        const confirmPasswordVal = joinJS.passwordConfirmInput.val()
        if(passwordVal && confirmPasswordVal) {
            if(passwordVal !== confirmPasswordVal) joinJS.passwordConfirmInput.addClass('is-invalid')
            else joinJS.passwordConfirmInput.removeClass('is-invalid')
        }
        if(passwordVal) {
            if(passwordVal.length >= 8 && passwordVal.length <= 20) joinJS.passwordInput.removeClass('is-invalid')
            else joinJS.passwordInput.addClass('is-invalid')
        }
    },

    saveForm: function () {

        const $form = $("#form")

        if(!joinJS.checkUserId) {
            alert("아이디 중복 확인을 해주세요.")
            return false
        }

        if(!joinJS.checkUserEmail) {
            alert("이메일 중복 확인을 해주세요.")
            return false
        }

        if (!$form[0].checkValidity()) {
            $form.addClass("was-validated");
        } else {
            const obj = {
                userId: joinJS.userIdInput.val(),
                userEmail: joinJS.userEmailInput.val(),
                password: joinJS.passwordInput.val(),
                userNm: $("#userNm").val()
            }
            Http.post('/snipp/api/user', obj).then(() => {
                alert("회원가입이 완료되었습니다.")
                window.location.href = '/join3'
            }).fail((e) =>  {
               console.log("e : ", e)
            });
        }
    }

}