'use strict';
app.controller('InfoController', ['$state', 'STATE', 'PostFactory', 'TopicFactory', 'FlashService', function($state, STATE, PostFactory, TopicFactory, FlashService) {

	var self = this;
	self.count = 0;

	function init(state) {
		switch (state) {
			case STATE.TOPICS:
				getUserTopicsCount();
				break;
		}
	}

	function getUserTopicsCount() {
		TopicFactory.getUserTopicsCount(function(response) {
			if (response.success) {
				self.count = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name);

}]);