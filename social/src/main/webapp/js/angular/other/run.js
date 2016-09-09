'use strict';
app.run(['$cookies', '$rootScope', '$state', 'FlashService', function($cookies, $rootScope, $state, FlashService) {

	$rootScope.$state = $state;
	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});

}]);