'use strict';
app.service('ObserverService', ['$q', 'STATE', function($q, STATE) {
	return {
		'responseError': function(errorResponse) {
			switch (errorResponse.status) {
				case 403:
					$injector.get('$state').transitionTo(STATE.LOGIN);
					break;
			}
			return $q.reject(errorResponse);
		}
	};
}]);