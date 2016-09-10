'use strict';
app.run(['$cookies', '$rootScope', '$state', 'FlashService', function($cookies, $rootScope, $state, FlashService) {

	$rootScope.$state = $state;
	$rootScope.user = $cookies.getObject('user');
	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});

}]);