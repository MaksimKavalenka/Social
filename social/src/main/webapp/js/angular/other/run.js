'use strict';
app.run(['$cookies', '$rootScope', '$state', 'UserFactory', 'FlashService', function($cookies, $rootScope, $state, UserFactory, FlashService) {

	$rootScope.$state = $state;

	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});

	UserFactory.getUser(function(response) {
		if (response !== null) {
			$rootScope.user = {id: response.id};
		}
	});

}]);