// Signup: validate password and confirm password match

document.addEventListener('DOMContentLoaded', function () {
    var clientForm = document.getElementById('client-signup-form');
    var developerForm = document.getElementById('developer-signup-form');

    // check password and confirm match; set browser validation message
    function validatePasswordMatch(form) {
        var password = form.querySelector('input[name="password"]');
        var confirm = form.querySelector('input[name="confirmPassword"]');
        if (!password || !confirm) return true;
        if (confirm.value !== password.value) {
            confirm.setCustomValidity('Passwords do not match');
            return false;
        }
        confirm.setCustomValidity('');
        return true;
    }

    // client signup form: validate on input and submit
    if (clientForm) {
        var clientConfirm = clientForm.querySelector('input[name="confirmPassword"]');
        if (clientConfirm) {
            clientConfirm.addEventListener('input', function () {
                validatePasswordMatch(clientForm);
            });
        }
        clientForm.addEventListener('submit', function (e) {
            if (!validatePasswordMatch(clientForm)) {
                e.preventDefault();
            }
        });
    }

    // developer signup form: validate on input and submit
    if (developerForm) {
        var devConfirm = developerForm.querySelector('input[name="confirmPassword"]');
        if (devConfirm) {
            devConfirm.addEventListener('input', function () {
                validatePasswordMatch(developerForm);
            });
        }
        developerForm.addEventListener('submit', function (e) {
            if (!validatePasswordMatch(developerForm)) {
                e.preventDefault();
            }
        });
    }
});
