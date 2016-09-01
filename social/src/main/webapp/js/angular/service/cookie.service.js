'use strict';
app.service('CookieService', ['$cookies', '$rootScope', function($cookies, $rootScope) {

	function setCredentials(user) {
		$rootScope.user = {
			id: user.id,
			login: user.login,
			role: user.role.name
		};
		$cookies.putObject('user', $rootScope.user);
	}

	function clearCredentials() {
		$rootScope.user = null;
		$cookies.remove('user');
	}

	return {
		setCredentials: setCredentials,
		clearCredentials: clearCredentials
	};
}]);