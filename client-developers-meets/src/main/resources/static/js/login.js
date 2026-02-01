// Login choice page: keyboard / accessibility (Enter to activate option)

document.addEventListener('DOMContentLoaded', function () {
    var clientOption = document.getElementById('client-option');
    var developerOption = document.getElementById('developer-option');

    // allow Enter key to activate client option
    if (clientOption) {
        clientOption.addEventListener('keydown', function (e) {
            if (e.key === 'Enter') e.target.click();
        });
    }
    // allow Enter key to activate developer option
    if (developerOption) {
        developerOption.addEventListener('keydown', function (e) {
            if (e.key === 'Enter') e.target.click();
        });
    }
});
