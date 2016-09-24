'use strict';
app.config(['$locationProvider', '$stateProvider', '$urlRouterProvider', 'CONTROLLER', 'PATH', 'STATE', 'TITLE', 'URL', function($locationProvider, $stateProvider, $urlRouterProvider, CONTROLLER, PATH, STATE, TITLE, URL) {

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
		templateUrl: PATH.SEARCH_TOOL
	}
	var pagination = {
		controller: CONTROLLER.PAGINATION_CONTROLLER,
		controllerAs: CONTROLLER.CTRL,
		templateUrl: PATH.PAGINATION_TOOL
	}

	$locationProvider.html5Mode(true);

	$stateProvider
	.state(STATE.LOGIN, {
		title: TITLE.LOGIN,
		url: URL.LOGIN,
		views: {
			header: welcomeHeader,
			content: {
				controller: CONTROLLER.USER_CONTROLLER,
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
				controller: CONTROLLER.USER_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.REGISTER_FORM
			}
		},
		footer: footer
	})
	.state(STATE.PROFILE, {
		title: TITLE.PROFILE,
		url: URL.PROFILE,
		views: {
			header: header,
			content: {
				controller: CONTROLLER.USER_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.PROFILE_FORM
			}
		},
		footer: footer
	})
	.state(STATE.TOPIC_ADD, {
		title: TITLE.TOPIC_ADD,
		url: URL.TOPIC_ADD,
		views: {
			header: header,
			content: {
				controller: CONTROLLER.TOPIC_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.TOPIC_EDIT_FORM
			},
			footer: footer
		}
	})
	.state(STATE.TOPIC_EDIT, {
		url: URL.TOPIC_EDIT,
		views: {
			header: header,
			content: {
				controller: CONTROLLER.TOPIC_CONTROLLER,
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
			pagination: pagination,
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
		params: {
			page: '1'
		},
		views: {
			header: header,
			pagination: pagination,
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
			pagination: pagination,
			info: {
				controller: CONTROLLER.INFO_CONTROLLER,
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
	.state(STATE.POST, {
		title: TITLE.POST,
		url: URL.POST,
		views: {
			header: header,
			content: {
				controller: CONTROLLER.POST_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.POST_CONTENT
			},
			footer: footer
		}
	})
	.state(STATE.NOTIFICATIONS, {
		title: TITLE.NOTIFICATIONS,
		url: URL.NOTIFICATIONS,
		params: {
			page: '1'
		},
		views: {
			header: header,
			pagination: pagination,
			content: {
				controller: CONTROLLER.NOTIFICATION_CONTROLLER,
				controllerAs: CONTROLLER.CTRL,
				templateUrl: PATH.NOTIFICATION_CONTENT
			},
			footer: footer
		}
	})
	.state(STATE.SEARCH, {
		title: TITLE.SEARCH,
		url: URL.SEARCH,
		params: {
			page: '1',
			value: null
		},
		views: {
			header: header,
			search: search,
			pagination: pagination,
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