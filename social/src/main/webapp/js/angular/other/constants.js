'use strict';
app.constant('CONTROLLER', (function() {
	return {
		CTRL: 'ctrl',
		POST_CONTROLLER: 'PostController',
		POST_EDIT_CONTROLLER: 'PostEditController',
		TOPIC_CONTROLLER: 'TopicController',
		TOPIC_EDIT_CONTROLLER: 'TopicEditController',
		USER_EDIT_CONTROLLER: 'UserEditController'
	}
})());

app.constant('MESSAGE', (function() {
	var creatingError = 'Error while creating ';
	var updatingError = 'Error while updating ';
	var gettingError = 'Error while getting ';
	return {
		FORM_ERROR: 'Required fields must be filled',
		AUTHENTICATION_ERROR: 'Login or password is wrong',
		TAKEN_LOGIN_ERROR: 'This login is already taken',
		TAKEN_PATH_ERROR: 'This path is already taken',
		SAVING_FILE_ERROR: 'Error while saving file',
		CREATING_POST_ERROR: creatingError + 'post',
		CREATING_TOPIC_ERROR: creatingError + 'topic',
		CREATING_USER_ERROR: creatingError + 'user',
		UPDATING_POST_ERROR: updatingError + 'post',
		UPDATING_TOPIC_ERROR: updatingError + 'topic',
		UPDATING_USER_ERROR: updatingError + 'user',
		GETTING_POST_ERROR: gettingError + 'post',
		GETTING_TOPIC_ERROR: gettingError + 'topic',
		GETTING_USER_ERROR: gettingError + 'user'
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
		POST_CONTENT: contentPath + '/post' + htmlExt,
		TOPIC_CONTENT: contentPath + '/topic' + htmlExt,
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
		POSTS: url + '/posts',
		TOPICS: url + '/topics',
		USERS: url + '/users'
	}
})());

app.constant('STATE', (function() {
	var topic = 'topic';
	var addOperation = '_add';
	return {
		LOGIN: 'login',
		REGISTER: 'register',
		TOPIC_ADD: topic + addOperation,
		FEED: 'feed',
		TOPIC: topic,
		TOPICS: 'topics',
		SEARCH: 'search',
	}
})());

app.constant('TITLE', (function() {
	return {
		LOGIN: 'Login',
		REGISTER: 'Register',
		FEED: 'Feed',
		TOPICS: 'Topics',
		SEARCH: 'Search'
	}
})());

app.constant('URL', (function() {
	var url = '/social';
	var feedUrl = '/feed';
	var topicUrl = '/topic';
	var topicsUrl = '/topics';
	var addOperation = '/add';
	var idKey = '{id:[0-9]{1,}}';
	var pageKey = '{page:[0-9]{1,}}';
	var pathKey = '{path:[a-z0-9._]{1,}}';
	var valueKey = '{value:(.){1,}}';
	return {
		HOME: url + feedUrl + '?page=1',
		LOGIN: url + '/login',
		REGISTER: url + '/register',
		TOPIC_ADD: url + topicUrl + addOperation,
		FEED: url + feedUrl + '?' + pageKey,
		TOPIC: url + topicUrl + '/' + pathKey + '?' + pageKey,
		TOPICS: url + topicsUrl + '?' + pageKey,
		SEARCH: url + '/search' + '?' + valueKey + '&' + pageKey
	}
})());