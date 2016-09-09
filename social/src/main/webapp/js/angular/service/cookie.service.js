'use strict';
app.service('CookieService', ['$cookies', function($cookies) {

	function createRememberMeCookie(userdetials) {
		var name = userdetials.username;
		var pwd = userdetials.password;
		var expireDate = new Date();
		expireDate.setDate(expireDate.getDate() + 30);
		var cookieValue = btoa(name + ":" + expireDate.getTime().toString() + ":" + md5(name + ":" + expireDate.getTime().toString() + ":" + pwd + ":" + "DEVELNOTES_REMEMBER_TOKEN"));
		$cookies.put('DEVELNOTES_REMEMBER_ME_COOKIE', cookieValue, {'expires': expireDate, 'path': '/social/'});
	}

	function removeRememberMeCookie() {
		var expireDate = new Date();
		expireDate.setDate(expireDate.getDate() - 1);
		$cookies.put('DEVELNOTES_REMEMBER_ME_COOKIE', '', {'expires': expireDate, 'path': '/social/'});
	}

	return {
		createRememberMeCookie: createRememberMeCookie,
		removeRememberMeCookie: removeRememberMeCookie
	};

}]);