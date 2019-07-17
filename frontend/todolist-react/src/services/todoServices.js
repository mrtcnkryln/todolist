
import axios from 'axios';
import { Constants } from '../utils/config';

const jsonConfig = {
    headers: { 'content-type': 'application/json;charset=UTF-8' }
}

class TodoServices {

    login(username, password) {
        let formData = new FormData();
        formData.set('username', username);
        formData.set('password', password);

        const config = {
            headers: { 'content-type': 'multipart/form-data' }
        }
        return axios.post(Constants.apiBaseURL + "user/login", formData, config);
    }

    register(username, password, name, surname) {
        var dataItem = {
            "username": username,
            "password": password,
            "name": name,
            "surname": surname
        };
        return axios.post(Constants.apiBaseURL + "user/register", dataItem, jsonConfig);
    }

    getUserList(id) {
        return axios.get(Constants.apiBaseURL + "todolist/user/" + id, jsonConfig);
    }

    createTodoList(name, userId) {
        var dataItem = {
            "user": {
                "id": userId,
            },
            "name": name
        };
        return axios.post(Constants.apiBaseURL + "todolist/create", dataItem, jsonConfig);
    }

    deleteTodoList(id, userId) {
        return axios.delete(Constants.apiBaseURL + "todolist/delete/" + id + "/" + userId, jsonConfig);
    }

    getTodoItems(listId) {
        return axios.get(Constants.apiBaseURL + "todoitem/list/" + listId, jsonConfig);
    }

    createTodoItem(name,description,state,deadline, listId,userId) {
        var dataItem = {
            "todoList": {
                "id": listId,
            },
            "name": name,
            "description": description,
            "state":state,
            "deadline":deadline
        };
        return axios.post(Constants.apiBaseURL + "todoitem/create", dataItem, jsonConfig);
    }

    deleteTodoItem(itemId, listId) {
        return axios.delete(Constants.apiBaseURL + "todoitem/delete/" + itemId + "/" + listId, jsonConfig);
    }

    getNotDependTodoItems(itemId,listId) {
        return axios.get(Constants.apiBaseURL + "todoitem/notDependent/" + itemId + "/" + listId, jsonConfig);
    }

    getDependenyItems(itemId) {
        return axios.get(Constants.apiBaseURL + "todoitem/dependency/" + itemId, jsonConfig);
    }

    createDependency(todoItemId,dependentItemId) {
        var dataItem = {
            "itemFrom": {
                "id": todoItemId,
            },
            "itemTo": {
                "id": dependentItemId,
            },
        };
        return axios.post(Constants.apiBaseURL + "todoitem/dependency/create", dataItem, jsonConfig);
    }

    deleteDependency(dependencyId, itemId) {
        return axios.delete(Constants.apiBaseURL + "todoitem/delete/dependency/" + dependencyId + "/" + itemId, jsonConfig);
    }

    markTodoItem(itemId,listId,state) {
        let formData = new FormData();
        formData.set('listId', listId);
        formData.set('itemId', itemId);
        formData.set('state', state);
        const config = {
            headers: { 'content-type': 'multipart/form-data' }
        }
        return axios.post(Constants.apiBaseURL + "todoitem/mark", formData, config);
    }

} export default TodoServices;