'use strict';
app.controller('AutocompleteController', ['$rootScope', '$scope', '$state', 'UserFactory', 'FlashService', function ($rootScope, $scope, $state, UserFactory, FlashService) {

	$rootScope.users = [];
	$rootScope.models = [];
	$scope.global = $rootScope;

	$rootScope.initModels = function(creatorId) {
		if ($rootScope.user.id === creatorId) {
			UserFactory.getUsersForInvitation($state.params.path, function(response) {
				if (response.success) {
					$rootScope.models = response.data;
				} else {
					FlashService.error(response.message);
				}
			});
		}
	};

	$rootScope.clearModels = function() {
		$rootScope.users = [];
		$rootScope.models = [];
		document.getElementById('search').reset();
    }

	$scope.remove = function(user) {
		var index = $rootScope.users.indexOf(user);
		if (index >= 0) {
			$rootScope.users.splice(index, 1);
		}
		$rootScope.models.push(user);
    }

	$scope.options = {
		suggest: suggestNames
	};

	function highlight(str, term) {
		var highlightRegex = new RegExp('(' + term + ')', 'gi');
		return str.replace(highlightRegex, '<div class="ac-highlight">$1</div>');
	}

	function suggestNames(term) {
		var q = term.toLowerCase().trim();
		var results = [];
		for (var i = 0; i < $rootScope.models.length && results.length < 10; i++) {
			var model = $rootScope.models[i];
			if (model.login.toLowerCase().indexOf(q) >= 0) {
				results.push({
					model: model,
					value: highlight(model.login, term)
				});
			}
		}
		return results;
	}

}]);