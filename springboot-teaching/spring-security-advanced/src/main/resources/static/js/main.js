// main.js
// Password visibility toggle
document.addEventListener('DOMContentLoaded', function() {
    const toggleButtons = document.querySelectorAll('.toggle-password');

    toggleButtons.forEach(button => {
        button.addEventListener('click', function() {
            const input = this.parentElement.querySelector('input');
            const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);

            // Update icon (optional visual feedback)
            const svg = this.querySelector('svg');
            if (type === 'text') {
                svg.style.opacity = '0.7';
            } else {
                svg.style.opacity = '1';
            }
        });
    });

    // Form submission loading state
    const loginForm = document.getElementById('loginForm');
    const submitBtn = document.getElementById('submitBtn');

    if (loginForm && submitBtn) {
        loginForm.addEventListener('submit', function(e) {
            const btnText = submitBtn.querySelector('.btn-text');
            const btnLoader = submitBtn.querySelector('.btn-loader');

            if (btnText && btnLoader) {
                btnText.style.display = 'none';
                btnLoader.style.display = 'inline-block';
                submitBtn.disabled = true;
            }
        });
    }

    // Smooth input animations
    const inputs = document.querySelectorAll('.input-group input');
    inputs.forEach(input => {
        // Check if input has value on load
        if (input.value) {
            input.dispatchEvent(new Event('input'));
        }

        input.addEventListener('focus', function() {
            this.parentElement.querySelector('.input-icon')?.classList.add('focused');
        });

        input.addEventListener('blur', function() {
            if (!this.value) {
                this.parentElement.querySelector('.input-icon')?.classList.remove('focused');
            }
        });
    });

    // Auto-hide alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => {
                if (alert.parentNode) {
                    alert.style.display = 'none';
                }
            }, 300);
        }, 5000);
    });

    // Add floating label animation for demo credentials display
    const demoBadges = document.querySelectorAll('.cred-badge');
    demoBadges.forEach(badge => {
        badge.addEventListener('click', function() {
            const credText = this.textContent.split(' / ');
            if (credText.length === 2) {
                const usernameInput = document.getElementById('username');
                const passwordInput = document.getElementById('password');
                if (usernameInput && passwordInput) {
                    usernameInput.value = credText[0];
                    passwordInput.value = credText[1];
                    usernameInput.dispatchEvent(new Event('input'));
                    passwordInput.dispatchEvent(new Event('input'));
                }
            }
        });
    });
});