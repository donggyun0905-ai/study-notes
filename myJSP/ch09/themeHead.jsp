<%@page pageEncoding="UTF-8"%>
<script>
	tailwind.config = { darkMode: 'class' };
	(function () {
		try {
			var theme = localStorage.getItem('theme');
			if (theme === 'dark' || (!theme && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
				document.documentElement.classList.add('dark');
			} else {
				document.documentElement.classList.remove('dark');
			}
		} catch (e) {}
	})();
</script>
