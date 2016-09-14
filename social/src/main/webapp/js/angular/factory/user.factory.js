'use strict';
app.factory('UserFactory', ['$http', 'MESSAGE', 'REST', 'CookieService', function($http, MESSAGE, REST, CookieService) {

	function createUser(login, password, callback) {
		$http.post(REST.USERS + '/create/' + login + '/' + password + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function() {
			var response = {success: false, message: MESSAGE.CREATING_USER_ERROR};
			callback(response);
		});
	}

	function authentication(login, password, remember, callback) {
		if (!login || !password) {
			var response = {success: false, message: MESSAGE.FORM_ERROR};
			callback(response);
			return;
		}
		var headers = {authorization : "Basic " + btoa(login + ":" + password)};
		$http.get(REST.USERS + '/auth' + REST.JSON_EXT, {'headers' : headers})
		.success(function(response) {
			if (response) {
				if (remember) {
					CookieService.createRememberMeCookie(response);
				}
				var data = {success: true, data: response};
				callback(data);
			} else {
				response = {success: false, message: MESSAGE.AUTHENTICATION_ERROR};
				callback(response);
			}
		})
		.error(function() {
			var response = {success: false, message: MESSAGE.GETTING_USER_ERROR};
			callback(response);
		});
	}

	function getUser(callback) {
		$http.get(REST.USERS + '/auth' + REST.JSON_EXT, {})
		.success(function(response) {
			if (response) {
				callback(response);
			} else {
				callback(null);
			}
		})
		.error(function() {
			callback(null);
		});
	}

	function logout() {
		CookieService.removeRememberMeCookie();
		$http.post(REST.USERS + '/logout' + REST.JSON_EXT, {});
	}

	function checkLogin(login, callback) {
		$http.post(REST.USERS + '/check_login/' + login + REST.JSON_EXT)
		.success(function(response) {
			if (response) {
				response = {success: true};
			} else {
				response = {success: false, message: MESSAGE.TAKEN_LOGIN_ERROR};
			}
			callback(response);
		})
		.error(function() {
			var response = {success: false, message: MESSAGE.GETTING_USER_ERROR};
			callback(response);
		});
	}

	return {
		createUser: createUser,
		authentication: authentication,
		getUser: getUser,
		logout: logout,
		checkLogin: checkLogin
	};

}]);