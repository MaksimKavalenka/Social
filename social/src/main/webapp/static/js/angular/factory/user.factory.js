'use strict';
app.factory('UserFactory', ['$http', 'MESSAGE', 'REST', 'ValidatorService', function($http, MESSAGE, REST, ValidatorService) {

	function createUser(login, password, confirmPassword, callback) {
		if (!ValidatorService.allNotEmpty(callback, login, password, confirmPassword)) {
			return;
		}
		if (password !== confirmPassword) {
			var response = {success: false, message: MESSAGE.PASSWORDS_ERROR};
			callback(response);
			return;
		}
		$http.post(REST.USERS + '/create/' + login + '/' + password + '/' + confirmPassword + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	function updateUser(login, currentPassword, password, confirmPassword, callback) {
		if (!ValidatorService.allNotEmpty(callback, login, currentPassword, password, confirmPassword)) {
			return;
		}
		if (password !== confirmPassword) {
			var response = {success: false, message: MESSAGE.PASSWORDS_ERROR};
			callback(response);
			return;
		}
		$http.post(REST.USERS + '/update/' + login + '/' + currentPassword + '/' + password + '/' + confirmPassword + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	function updateUserLogin(login, currentPassword, callback) {
		if (!ValidatorService.allNotEmpty(callback, login, currentPassword)) {
			return;
		}
		$http.post(REST.USERS + '/update/' + login + '/' + currentPassword + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	function updateUserPhoto(photo, callback) {
		if (!ValidatorService.allNotEmpty(callback, photo)) {
			return;
		}
		$http.post(REST.USERS + '/update/' + photo + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	function getUser(callback) {
		$http.get(REST.USERS + '/auth' + REST.JSON_EXT, {})
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_USER_ERROR};
			callback(response);
		});
	}

	function getUsersForInvitation(path, callback) {
		if (!ValidatorService.allNotEmpty(callback, path)) {
			return;
		}
		$http.post(REST.USERS + '/' + path + '/for_invitation' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data:response};
			callback(data);
		})
		.error(function() {
			var response = {success: false, message: MESSAGE.GETTING_USER_ERROR};
			callback(response);
		});
	}

	function authentication(login, password, callback) {
		if (!ValidatorService.allNotEmpty(callback, login, password)) {
			return;
		}
		var headers = {authorization : "Basic " + btoa(login + ":" + password)};
		$http.get(REST.USERS + '/auth' + REST.JSON_EXT, {'headers' : headers})
		.success(function(response) {
			if (response) {
				var data = {success: true, data: response};
				callback(data);
			} else {
				response = {success: false, message: MESSAGE.AUTHENTICATION_ERROR};
				callback(response);
			}
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.AUTHENTICATION_ERROR};
			callback(response);
		});
	}

	function logout() {
		$http.post(REST.USERS + '/logout' + REST.JSON_EXT, {});
	}

	function checkLogin(login, callback) {
		if (!ValidatorService.allNotEmpty(callback, login)) {
			return;
		}
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
		updateUser: updateUser,
		updateUserLogin: updateUserLogin,
		updateUserPhoto: updateUserPhoto,
		getUser: getUser,
		getUsersForInvitation: getUsersForInvitation,
		authentication: authentication,
		logout: logout,
		checkLogin: checkLogin
	};

}]);