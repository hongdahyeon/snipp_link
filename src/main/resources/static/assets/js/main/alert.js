class Simple {

    /**
     * 출처 : https://github.com/simple-notify/simple-notify
     * */
    static options = {
        speed: 400
        ,autoClose: false
        ,autotimeout: 2500
        ,effect: "fade"             // slide, fade
        ,showCloseButton: true
        ,showIcon: true
        ,gap: 20
        ,distance: 20
        ,position: 'right bottom'   // top, right, bottom, left, x-center, y-center, center
        ,type: "outline"            // outline, filled
    }

    static alert(text, title = '') {
        new Notify({
            ...Simple.options,
            status: 'info',
            title,
            text
        })
    }

    static success(text, title = '') {
        new Notify({
            ...Simple.options,
            status: 'success',
            title,
            text
        })
    }

    static warn(text, title = '') {
        new Notify({
            ...Simple.options,
            status: 'warning',
            title,
            text
        })
    }

    static error(text, title = '') {
        new Notify({
            ...Simple.options,
            status: 'error',
            title,
            text
        })
    }
}

class Sweet {

    /**
     * 출처 : https://sweetalert2.github.io/
     * */
    static confirm(html, confirmButtonText = '확인', cancelButtonText = '취소') {
        return Swal.fire({
            html,
            focusConfirm: false,
            showCancelButton: true,
            customClass: {
                confirmButton: 'alert-confirm-btn',
                cancelButton: 'alert-cancel-btn'
            },
            confirmButtonText: confirmButtonText,
            cancelButtonText: cancelButtonText,
        }).then((res) => res.isConfirmed)
    }

    static alert(html) {
        return Swal.fire({
            html,
            customClass: {
                confirmButton: 'alert-confirm-btn'
            },
            focusConfirm: false,
            confirmButtonText: "확인"
        });
    }
}