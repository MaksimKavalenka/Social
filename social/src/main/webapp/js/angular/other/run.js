'use strict';
app.run(['$cookies', '$rootScope', 'FlashService', function($cookies, $rootScope, FlashService) {
	$rootScope.user = $cookies.getObject('user');
	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});
}]);