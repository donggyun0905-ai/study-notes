<%@page pageEncoding="UTF-8"%>
<div class="fixed bottom-5 right-5 z-50">
	<div class="flex overflow-hidden rounded-full border border-slate-200 bg-white/90 shadow-lg backdrop-blur dark:border-slate-600 dark:bg-slate-800/90">
		<button type="button" onclick="setTheme('light')"
			class="theme-light-btn px-3 py-2 text-xs font-semibold text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-700"
			aria-label="라이트 테마">
			Light
		</button>
		<button type="button" onclick="setTheme('dark')"
			class="theme-dark-btn px-3 py-2 text-xs font-semibold text-slate-600 hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-700"
			aria-label="다크 테마">
			Dark
		</button>
	</div>
</div>
<script>
	function setTheme(theme) {
		if (theme === 'dark') {
			document.documentElement.classList.add('dark');
		} else {
			document.documentElement.classList.remove('dark');
		}
		try { localStorage.setItem('theme', theme); } catch (e) {}
		syncThemeButtons();
	}
	function syncThemeButtons() {
		var dark = document.documentElement.classList.contains('dark');
		var lightBtn = document.querySelector('.theme-light-btn');
		var darkBtn = document.querySelector('.theme-dark-btn');
		if (!lightBtn || !darkBtn) return;
		lightBtn.classList.toggle('bg-indigo-600', !dark);
		lightBtn.classList.toggle('text-white', !dark);
		darkBtn.classList.toggle('bg-indigo-600', dark);
		darkBtn.classList.toggle('text-white', dark);
	}
	syncThemeButtons();
</script>
