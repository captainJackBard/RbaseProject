(function () {
    'use strict';

    angular
        .module('bubbleBattStoreApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
