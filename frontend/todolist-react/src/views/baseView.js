import React from 'react';
import TodoServices from '../services/todoServices';

class BaseView extends React.Component {

    services = new TodoServices();

    checkSession() {
        var userJSON = localStorage.getItem("user");
        if (userJSON !== null) {
            var user = JSON.parse(userJSON);
            console.log(user.id);
        } else {
            this.props.history.push('/login' );
        }
    }

    getUserId() {
        var userJSON = localStorage.getItem("user");
        if (userJSON !== null) {
            var user = JSON.parse(userJSON);
            return user.id;
        } else {
            return 0;
        }
        
    }

} export default BaseView;