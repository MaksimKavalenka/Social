'use strict';
app.service('CookieService', ['$cookies', function($cookies) {

	function createRememberMeCookie(userdetials) {
		var name = userdetials.username;
		var pwd = userdetials.password;
		var expireDate = new Date();
		expireDate.setDate(expireDate.getDate() + 30);
		var cookieValue = btoa(name + ":" + expireDate.getTime().toString() + ":" + md5(name + ":" + expireDate.getTime().toString() + ":" + pwd + ":" + "DEVELNOTES_REMEMBER_TOKEN"));
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