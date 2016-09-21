'use strict';
app.constant('CONTROLLER', (function() {
	return {
		CTRL: 'ctrl',
		INFO_CONTROLLER: 'InfoController',
		NOTIFICATION_CONTROLLER: 'NotificationController',
		PAGINATION_CONTROLLER: 'PaginationController',
		POST_CONTROLLER: 'PostController',
		TOPIC_CONTROLLER: 'TopicController',
		USER_CONTROLLER: 'UserController'
	}
})());

app.constant('MESSAGE', (function() {
	var deletingError = 'Error while deleting ';
	var gettingError = 'Error while getting ';
	return {
		AUTHENTICATION_ERROR: 'Login or password is wrong',
		FORM_ERROR: 'Required fields must be filled',
		DELETING_NOTIFICATION_ERROR: deletingError + 'notification',
		GETTING_NOTIFICATION_ERROR: gettingError + 'notification',
		GETTING_POST_ERROR: gettingError + 'post',
		GETTING_TOPIC_ERROR: gettingError + 'topic',
		GETTING_USER_ERROR: gettingError + 'user',
		PASSWORDS_ERROR: 'Passwords do not match',
		SAVING_FILE_ERROR: 'Error while saving file',
		TAKEN_LOGIN_ERROR: 'This login is already taken',
		TAKEN_PATH_ERROR: 'This path is already taken'
	}
})());

app.constant('PATH', (function() {
	var path = 'social/html';
	var contentPath = path + '/content';
	var formPath = path + '/form';
	var infoPath = path + '/info';
	var titlePath = path + '/title';
	var toolPath = path + '/tool';
	var htmlExt = '.html';
	return {
		COMMENT_CONTENT: contentPath + '/comment' + htmlExt,
		NOTIFICATION_CONTENT: contentPath + '/notification' + htmlExt,
		POST_CONTENT: contentPath + '/post' + htmlExt,
		TOPIC_CONTENT: contentPath + '/topic' + htmlExt,
		INVITE_FORM: formPath + '/invite' + htmlExt,
		LOGIN_FORM: formPath + '/login' + htmlExt,
		REGISTER_FORM: formPath + '/register' + htmlExt,
		TOPIC_EDIT_FORM: formPath + '/topic.edit' + htmlExt,
		TOPIC_INFO: infoPath + '/topic.info' + htmlExt,
		TOPICS_INFO: infoPath + '/topics.info' + htmlExt,
		FOOTER: titlePath + '/footer' + htmlExt,
		HEADER: titlePath + '/header' + htmlExt,
		WELCOME_HEADER: titlePath + '/welcome.header' + htmlExt,
		PAGINATION_TOOL: toolPath + '/pagination' + htmlExt,
		SEARCH_TOOL: toolPath + '/search' + htmlExt
	}
})());

app.constant('REST', (function() {
	var url = '/social';
	return {
		JSON_EXT: '.json',
		NOTIFICATIONS: url + '/notifications',
		POSTS: url + '/posts',
		TOPICS: url + '/topics',
		USERS: url + '/users'
	}
})());

app.constant('STATE', (function() {
	var topic = 'topic';
	var addOperation = '_add';
	var editOperation = '_edit';
	return {
		LOGIN: 'login',
		REGISTER: 'register',
		TOPIC_ADD: topic + addOperation,
		TOPIC_EDIT: topic + editOperation,
		FEED: 'feed',
		TOPIC: topic,
		TOPICS: 'topics',
		POST: 'post',
		NOTIFICATIONS: 'notifications',
		SEARCH: 'search'
	}
})());

app.constant('TITLE', (function() {
	return {
		LOGIN: 'Login',
		REGISTER: 'Register',
		TOPIC_ADD: 'Create topic',
		FEED: 'Feed',
		TOPICS: 'Topics',
		NOTIFICATIONS: 'Notifications',
		SEARCH: 'Search'
	}
})());

app.constant('URL', (function() {
	var url = '/social';
	var loginUrl = '/login';
	var registerUrl = '/register';
	var feedUrl = '/feed';
	var notificationsUrl = '/notifications';
	var searchUrl = '/search';
	var topicUrl = '/topic';
	var topicsUrl = '/topics';
	var addOperation = '/add';
	var editOperation = '/edit';
	var pageKey = '{page:[0-9]{1,}}';
	var pathKey = '{path:[a-z0-9._]{1,}}';
	var postKey = '{post:[0-9]{1,}}';
	var valueKey = '{value}';
	return {
		HOME: url + feedUrl + '?page=1',
		LOGIN: url + loginUrl,
		REGISTER: url + registerUrl,
		TOPIC_ADD: url + topicUrl + addOperation,
		TOPIC_EDIT: url + topicUrl + '/' + pathKey + editOperation,
		FEED: url + feedUrl + '?' + pageKey,
		TOPIC: url + topicUrl + '/' + pathKey + '?' + pageKey,
		TOPICS: url + topicsUrl + '?' + pageKey,
		POST: url + topicsUrl + '?' + postKey,
		NOTIFICATIONS: url + notificationsUrl + '?' + pageKey,
		SEARCH: url + searchUrl + '?' + valueKey + '&' + pageKey
	}
})());