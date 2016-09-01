'use strict';
app.config(['$cookiesProvider', '$locationProvider', function($cookiesProvider, $locationProvider) {
	$cookiesProvider.defaults.expires = new Date(new Date().getTime() + 604800000);
	$locationProvider.html5Mode(true);
}]);
app.config(['$stateProvider', '$urlRouterProvider', 'CONTROLLER', 'PATH', 'STATE', 'URL', function($stateProvider, $urlRouterProvider, CONTROLLER, PATH, STATE, URL) {
	var header = {
		templateUrl: PATH.HEADER
	}
	var footer = {
		templateUrl: PATH.FOOTER
	}
	$stateProvider
	.state(STATE.LOGIN, {
		url: URL.LOGIN,
		views: {
			content: {
				controller: CONTROLLER.USER_EDIT_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.LOGIN_FORM
			}
		}
	})
	.state(STATE.REGISTER, {
		url: URL.REGISTER,
		views: {
			content: {
				controller: CONTROLLER.USER_EDIT_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.REGISTER_FORM
			}
		}
	})
	.state(STATE.FEED, {
		url: URL.FEED,
		views: {
			header: header,
			content: {
				templateUrl: PATH.FEED_CONTENT
			},
			footer: footer
		}
	})
	$urlRouterProvider.otherwise(URL.HOME_PAGE);
}]);