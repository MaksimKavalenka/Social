'use strict';
app.controller('FormServise', ['$scope', '$state', 'STATE', 'PostFactory', 'FlashService', function($scope, $state, STATE, PostFactory, FlashService) {

	var self = this;
	self.post = {text: ""};
	self.form = false;

	self.showForm = function() {
		self.form = true;
	};

	self.showForm = function(postCreatorId, initText) {
		if ($scope.user.id === postCreatorId) {
			self.form = true;
			self.post = {text: initText};
		}
	};

	self.hideForm = function() {
		self.form = false;
		self.post = {text: ""};
	};

}]);