'use strict';
app.config(['$cookiesProvider', '$locationProvider', function($cookiesProvider, $locationProvider) {
	$cookiesProvider.defaults.path = "/social/";
	$cookiesProvider.defaults.expires = new Date(new Date().getTime() + 604800000);
	$locationProvider.html5Mode(true);
}]);
app.config(['$stateProvider', '$urlRouterProvider', 'CONTROLLER', 'PATH', 'STATE', 'URL', function($stateProvider, $urlRouterProvider, CONTROLLER, PATH, STATE, URL) {
	var footer = {
			templateUrl: PATH.FOOTER
		}
	var header = {
		templateUrl: PATH.HEADER
	}
	var welcomeHeader = {
			templateUrl: PATH.WELCOME_HEADER
		}
	$stateProvider
	.state(STATE.LOGIN, {
		url: URL.LOGIN,
		views: {
			header: welcomeHeader,
			content: {
				controller: CONTROLLER.USER_EDIT_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.LOGIN_FORM
			}
		},
		footer: footer
	})
	.state(STATE.REGISTER, {
		url: URL.REGISTER,
		views: {
			header: welcomeHeader,
			content: {
				controller: CONTROLLER.USER_EDIT_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.REGISTER_FORM
			}
		},
		footer: footer
	})
	.state(STATE.FEED, {
		url: URL.FEED,
		views: {
			header: header,
			tool: {
				controller: CONTROLLER.POST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			content: {
				controller: CONTROLLER.POST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.POST_CONTENT
			},
			footer: footer
		}
	})
	.state(STATE.TOPIC, {
		url: URL.TOPIC,
		views: {
			header: header,
			tool: {
				controller: CONTROLLER.POST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			content: {
				controller: CONTROLLER.POST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.POST_CONTENT
			},
			footer: footer
		}
	})
	.state(STATE.TOPICS, {
		url: URL.TOPICS,
		views: {
			header: header,
			tool: {
				controller: CONTROLLER.TOPIC_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			content: {
				controller: CONTROLLER.TOPIC_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TOPIC_CONTENT
			},
			footer: footer
		}
	})
	.state(STATE.TOPIC_ADD, {
		url: URL.TOPIC_ADD,
		views: {
			header: header,
			content: {
				controller: CONTROLLER.TOPIC_EDIT_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TOPIC_EDIT_FORM
			},
			footer: footer
		}
	})
	$urlRouterProvider.otherwise(URL.HOME_PAGE);
}]);