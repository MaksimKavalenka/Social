'use strict';
app.service('CookieService', ['$cookies', function($cookies) {

	function createRememberMeCookie(userdetials) {
		var login = userdetials.login;
		var password = userdetials.password;
		var expireDate = new Date();
		expireDate.setDate(expireDate.getDate() + 30);
		var cookieValue = btoa(login + ":" + expireDate.getTime().toString() + ":" + md5(login + ":" + expireDate.getTime().toString() + ":" + password + ":" + "DEVELNOTES_REMEMBER_TOKEN"));
		$cookies.putObject('DEVELNOTES_REMEMBER_ME_COOKIE', cookieValue, {'expires': expireDate, 'path': '/social/'});
	}

	function removeRememberMeCookie() {
		$cookies.remove('DEVELNOTES_REMEMBER_ME_COOKIE');
	}

	return {
		createRememberMeCookie: createRememberMeCookie,
		removeRememberMeCookie: removeRememberMeCookie
	};

}]);