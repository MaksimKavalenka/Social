'use strict';
var fileRequired = 'fileRequired';
var fileModel = 'fileModel';
var ngMatch = 'ngMatch';
var ngModel = 'ngModel';
var ngLoginExist = 'ngLoginExist';
var ngTopicNameExist = 'ngTopicNameExist';
var ngTopicUrlNameExist = 'ngTopicUrlNameExist';

app.directive(ngLoginExist, ['$timeout', '$q', 'UserFactory', function($timeout, $q, UserFactory) {
	var timer;
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			controller.$asyncValidators.ngLoginExist = function(modelValue, viewValue) {
				var def = $q.defer();
				$timeout.cancel(timer);
				timer = $timeout(function() {
					UserFactory.checkLogin(modelValue, function(response) {
						if (!response.success) {
							def.resolve();
						} else {
							def.reject();
						}
					});
				}, 1000);
				return def.promise;
			};
		}
	};
}]);

app.directive(ngTopicNameExist, ['$timeout', '$q', 'TopicFactory', function($timeout, $q, TopicFactory) {
	var timer;
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			controller.$asyncValidators.ngTopicNameExist = function(modelValue, viewValue) {
				var def = $q.defer();
				$timeout.cancel(timer);
				timer = $timeout(function() {
					TopicFactory.checkName(modelValue, function(response) {
						if (!response.success) {
							def.resolve();
						} else {
							def.reject();
						}
					});
				}, 1000);
				return def.promise;
			};
		}
	};
}]);

app.directive(ngTopicUrlNameExist, ['$timeout', '$q', 'TopicFactory', function($timeout, $q, TopicFactory) {
	var timer;
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			controller.$asyncValidators.ngTopicUrlNameExist = function(modelValue, viewValue) {
				var def = $q.defer();
				$timeout.cancel(timer);
				timer = $timeout(function() {
					TopicFactory.checkUrlName(modelValue, function(response) {
						if (!response.success) {
							def.resolve();
						} else {
							def.reject();
						}
					});
				}, 1000);
				return def.promise;
			};
		}
	};
}]);

app.directive(ngMatch, function() {
	return {
		require: ngModel,
		scope: {
			otherModelValue: "=" + ngMatch
		},
		link: function(scope, element, attributes, controller) {
			controller.$validators.ngMatch = function(modelValue) {
				return modelValue == scope.otherModelValue;
			};
			scope.$watch("otherModelValue", function() {
				controller.$validate();
			});
		}
	};
});

app.directive(fileRequired, function() {
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			element.bind('change', function() {
				scope.$apply(function() {
					controller.$setViewValue(element.val());
					controller.$render();
				});
			});
		}
	}
});

app.directive(fileModel, ['$parse', function($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attributes) {
			var model = $parse(attributes.fileModel);
			var modelSetter = model.assign;
			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]);