'use strict';
app.config(['$cookiesProvider', '$locationProvider', function($cookiesProvider, $locationProvider) {
	$cookiesProvider.defaults.path = "/social/";
	$cookiesProvider.defaults.expires = new Date(new Date().getTime() + 604800000);
	$locationProvider.html5Mode(true);
}]);

app.config(['$stateProvider', '$urlRouterProvider', 'CONTROLLER', 'PATH', 'STATE', 'TITLE', 'URL', function($stateProvider, $urlRouterProvider, CONTROLLER, PATH, STATE, TITLE, URL) {
	var footer = {
		templateUrl: PATH.FOOTER
	}
	var header = {
		templateUrl: PATH.HEADER
	}
	var welcomeHeader = {
		templateUrl: PATH.WELCOME_HEADER
	}
	var search = {
		controller: CONTROLLER.TOPIC_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.SEARCH_TOOL
	}

	$stateProvider
	.state(STATE.LOGIN, {
		title: TITLE.LOGIN,
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
		title: TITLE.REGISTER,
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
	.state(STATE.FEED, {
		title: TITLE.FEED,
		url: URL.FEED,
		params: {
			page: '1'
		},
		views: {
			header: header,
			pagination: {
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
		title: function($state) {
			return $state.params.path;
		},
		url: URL.TOPIC,
		params: {
			page: '1'
		},
		views: {
			header: header,
			pagination: {
				controller: CONTROLLER.POST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			info: {
				controller: CONTROLLER.TOPIC_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TOPIC_INFO
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
		title: TITLE.TOPICS,
		url: URL.TOPICS,
		params: {
			page: '1'
		},
		views: {
			header: header,
			pagination: {
				controller: CONTROLLER.TOPIC_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			info: {
				controller: CONTROLLER.TOPIC_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TOPICS_INFO
			},
			content: {
				controller: CONTROLLER.TOPIC_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TOPIC_CONTENT
			},
			footer: footer
		}
	})
	.state(STATE.SEARCH, {
		title: TITLE.SEARCH,
		url: URL.SEARCH,
		params: {
			page: '1',
			value: ' '
		},
		views: {
			header: header,
			search: search,
			pagination: {
				controller: CONTROLLER.TOPIC_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PAGINATION_TOOL
			},
			content: {
				controller: CONTROLLER.TOPIC_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TOPIC_CONTENT
			}
		},
		footer: footer
	});

	$urlRouterProvider.otherwise(URL.HOME);
}]);