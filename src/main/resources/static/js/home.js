// Dark Theme Logic
document.addEventListener("DOMContentLoaded", () => {
    const themeToggle = document.getElementById("theme-toggle");
    if (themeToggle) {
        let darkMode = localStorage.getItem("darkMode") === "true";

        function applyTheme() {
            document.documentElement.setAttribute("data-theme", darkMode ? "dark" : "light");
            themeToggle.textContent = darkMode ? "☀️" : "🌙";
        }

        themeToggle.addEventListener("click", () => {
            darkMode = !darkMode;
            localStorage.setItem("darkMode", darkMode);
            applyTheme();
        });

        applyTheme();
    }
}); 