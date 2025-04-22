var loginJS = {

    idItemKey: "SNIPP_LINK_REM_ID",
    errorP: $("#error-message"),

    /* 비밀번호 만료 */
    changePwdModal: $("#changePwdModal"),
    changePwdModalForm: $("#changePwdModalForm"),
    changePwdModalForm_userId: $("#changePwdModalForm-userId"),
    changePwdModalForm_password: $("#changePwdModalForm-password"),
    changePwdModalForm_confirmPassword: $("#changePwdModalForm-passwordConfirm"),

    /* 휴먼 계정 */
    accountExpiredModal: $("#accountExpiredModal"),
    accountExpiredModalForm: $("#accountExpiredModalForm"),
    accountExpiredModalForm_userId: $("#accountExpiredModalForm-userId"),
    accountExpiredModalForm_userEmail: $("#accountExpiredModalForm-userEmail"),
    accountExpiredModalForm_verifyCode: $("#accountExpiredModalForm-verifyCode"),
    accountExpiredModal_sendVerifyCodeBtn: $("#accountExpiredModal-send-verifyCode"),

    settingError: function(params) {
        const type = decodeURIComponent(params.get("type"))
        const errorMessage = decodeURIComponent(params.get("mssg"))
        const userId = decodeURIComponent(params.get("userId"))
        const userEmail = decodeURIComponent(params.get("userEmail"))
        loginJS.errorP.html(errorMessage.replace("\n", "<br>"));

        switch(type) {
            /* [사용자가 없을때] */
            case "none":
                loginJS.noneUser(errorMessage);
                break;

            /* [비번 만료] : 변경한지 90일 지났을때 */
            case "expired":
                loginJS.passwordExpired(userId, errorMessage);
                break;

            /* 폼 로그인 실패 : [소셜 로그인 실패], [비번 5회 오류로 계정 잠김], [내부 오류] */
            /* 소셜 로그인 실패 : [소셜 로그인 이메일이 중복되는 경우], [소셜 아이디를 잠근 경우] */
            case "error":
            case "socialEmailDuplicate":
            case "socialLock":
                loginJS.loginError(errorMessage);
                break;

            /* [계정 비활성화] : 관리자가 계정을 비활성화 */
            case "disable":
                loginJS.accountDisabled(userId, errorMessage);
                break;

            /* [계정 만료] : 로그인 1년이상 안해서 휴면 계정 */
            /* [소셜로그인 오류] : 소셜 아이디로 로그인한지 1년이 넘은 경우 */
            case "account":
            case "socialExpired":
                loginJS.accountExpired(userId, userEmail, errorMessage);
                break;

            /* [소셜로그인 오류] : 소셜 아이디를 비활성화한 경우 */
            case "socialEnable":
                loginJS.socialEnable(userId, errorMessage);
                break;
        }
    }

    /* [사용자가 없을때] */
    ,noneUser: function(message) {
        Simple.error(message.replace("\n", "<br>"), '로그인 실패');
    }

    /* [비번 만료] : 변경한지 90일 지났을때 */
    ,passwordExpired: function(userId, message) {
        loginJS.changePwdModalForm_userId.val(userId);
        loginJS.changePwdModal.modal('show');
    }

    /* 폼 로그인 실패 : [소셜 로그인 실패], [비번 5회 오류로 계정 잠김], [내부 오류] */
    /* 소셜 로그인 실패 : [소셜 로그인 이메일이 중복되는 경우], [소셜 아이디를 잠근 경우] */
    ,loginError: function(message) {
        Simple.error(message.replace("\n", "<br>"), '로그인 실패');
    }

    /* [계정 비활성화] : 관리자가 계정을 비활성화 */
    ,accountDisabled: function (userId, message) {
        Simple.error(message.replace("\n", "<br>"), '로그인 실패');
    }

    /* [계정 만료] : 로그인 1년이상 안해서 휴면 계정 */
    /* [소셜로그인 오류] : 소셜 아이디로 로그인한지 1년이 넘은 경우 */
    ,accountExpired: function (userId, userEmail, message) {
        Simple.error(message.replace("\n", "<br>"), '로그인 실패');
        loginJS.accountExpiredModalForm_userId.val(userId);
        loginJS.accountExpiredModalForm_userEmail.val(userEmail);
        loginJS.accountExpiredModal.modal('show')
    }

    /* [소셜로그인 오류] : 소셜 아이디를 비활성화한 경우 */
    ,socialEnable: function (userId, message) {
        Simple.error(message.replace("\n", "<br>"), '로그인 실패');
    }

    /* 비번 만료로 비밀번호 변경시 사용 */
    ,validatePassword: function () {
        const passwordVal = loginJS.changePwdModalForm_password.val()
        const confirmPasswordVal = loginJS.changePwdModalForm_confirmPassword.val()
        if(passwordVal && confirmPasswordVal) {
            if(passwordVal !== confirmPasswordVal) loginJS.changePwdModalForm_confirmPassword.addClass('is-invalid')
            else loginJS.changePwdModalForm_confirmPassword.removeClass('is-invalid')
        }
        if(passwordVal) {
            if(passwordVal.length >= 8 && passwordVal.length <= 20) loginJS.changePwdModalForm_password.removeClass('is-invalid')
            else loginJS.changePwdModalForm_password.addClass('is-invalid')
        }
    }

    /* === 비밀번호 변경한지 90일 지난 경우 === */
    /* (1) 90일 연장 */
    ,add90PwdChangeDays: function () {
        const obj = {
            change: false,
            userId: loginJS.changePwdModalForm_userId.val()
        }
        Http.put('/snipp/api/user/change-password', obj).then(() => {
            Sweet.alert("비밀번호가 연장되었습니다.").then(() => {
               window.location.href = '/login'
            });
        })
    }
    /* (2) 비밀번호 변경 */
    ,changePassword: function () {
        const $form = loginJS.changePwdModalForm;
        if (!$form[0].checkValidity()) {
            $form.addClass("was-validated");
        } else {
            const obj = {
                userId: loginJS.changePwdModalForm_userId.val(),
                password: loginJS.changePwdModalForm_password.val(),
                change: true
            }
            Http.put('/snipp/api/user/change-password', obj).then(() => {
                Sweet.alert("비밀번호가 수정되었습니다.").then(() => {
                    window.location.href = '/login'
                });
            })
        }
    }

    /* === 휴먼 계정 === */
    /* (1) 인증번호 발송 */
    ,sendVerifyCode: function () {
        const userEmail = loginJS.accountExpiredModalForm_userEmail.val()
        loginJS.accountExpiredModal_sendVerifyCodeBtn.prop('disabled', true).prop('readonly', true);
        Http.get('/snipp/api/user/is-expired', {userEmail}).then(() => {
           Sweet.alert('이메일로 인증번호가 발송되었습니다.');
        });
    }
    ,checkVerifyCode: function () {
        const $form = loginJS.accountExpiredModalForm;
        if (!$form[0].checkValidity()) {
            $form.addClass("was-validated");
        } else {
            const obj = {
                userEmail: loginJS.accountExpiredModalForm_userEmail.val(),
                verifyCode: loginJS.accountExpiredModalForm_verifyCode.val()
            }
            Http.put('/snipp/api/verify-code/check-verify-code', obj).then((res) => {
                if(res) {
                    Sweet.alert('휴먼 계정이 풀렸습니다.').then(() => {
                       window.location.href = '/login'
                    });
                } else {
                    Sweet.alert('휴먼 계정 풀기에 실패했습니다.');
                }
            })
        }
    }
}