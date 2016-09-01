'use strict';
app.constant('CONTROLLER', {
	'CTRL': 'ctrl',
	'USER_EDIT_CONTROLLER': 'UserEditController'
});
app.constant('MESSAGE', (function() {
	var addingError = 'Error while adding ';
	var updatingError = 'Error while updating ';
	var gettingError = 'Error while getting ';
	var addingSuccess = ' has been added successfully';
	return {
		AUTHENTICATION_ERROR: 'Login or password is wrong',
		TAKEN_LOGIN_ERROR: 'This login is already taken',
		SAVING_FILE_ERROR: 'Error while saving file',
		ADDING_USER_ERROR: addingError + 'user',
		UPDATING_USER_ERROR: updatingError + 'user',
		GETTING_USER_ERROR: gettingError + 'user',
	}
})());
app.constant('PATH', (function() {
	var path = 'social/html';
	var contentPath = path + '/content';
	var formPath = path + '/form';
	var titlePath = path + '/title';
	var toolPath = path + '/tool';
	var htmlExt = '.html';
	return {
		FEED_CONTENT: contentPath + '/feed' + htmlExt,
		LOGIN_FORM: formPath + '/login' + htmlExt,
		REGISTER_FORM: formPath + '/register' + htmlExt,
		HEADER: titlePath + '/header' + htmlExt,
		FOOTER: titlePath + '/footer' + htmlExt,
		PAGINATION_TOOL: toolPath + '/pagination' + htmlExt
	}
})());
app.constant('REST', (function() {
	var url = '/social';
	return {
		JSON_EXT: '.json',
		USERS: url + '/users'
	}
})());
app.constant('STATE', (function() {
	return {
		LOGIN: 'login',
		REGISTER: 'register',
		FEED: 'feed'
	}
})());
app.constant('URL', (function() {
	var url = '/social';
	return {
		HOME_PAGE: url + '/feed',
		LOGIN: url + '/login',
		REGISTER: url + '/register',
		FEED: url + '/feed'
	}
})());