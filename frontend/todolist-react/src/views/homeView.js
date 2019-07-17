import React from 'react';
import { createMuiTheme } from '@material-ui/core/styles';
import purple from '@material-ui/core/colors/purple';
import green from '@material-ui/core/colors/green';
import Exit from '@material-ui/icons/ExitToApp';
import Grid from '@material-ui/core/Grid';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';
import TextField from '@material-ui/core/TextField';
import bgImage from '../images/bg_image.jpg';  
import { Button } from '@material-ui/core';
import { Redirect } from 'react-router';
import MaterialTableDemo from '../functions/materialTable';
import BaseScreen from './baseView';


class Home extends BaseScreen {

    classes = createMuiTheme({
  palette: {
    primary: purple,
    secondary: green,
  },
  status: {
    danger: 'orange',
  },
});

    constructor(props) {
        super(props);
        this.state = {
            todoList: [],
            userId: this.getUserId(),
            tableColumns: [
                { title: 'User Todo List Name', field: 'name' }
            ],
        };

        this.handleLogout = this.handleLogout.bind(this);
    }

    handleLogout(event) {
        localStorage.clear();
        this.checkSession();
    }

    componentDidMount() {
        this.checkSession();
        this.getTodoList();
    }

    getTodoList(){
        this.services.getUserList(this.state.userId).then(resp => {
            if (resp.data.success) {
                this.setState({ todoList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
            console.log(resp.data);
        })
    }

    
    createTodoList(name) {
        this.services.createTodoList(name, this.state.userId).then(resp => {
            if (resp.data.success) {
                this.setState({ todoList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
        });
    }

    deleteTodoList(id) {
        this.services.deleteTodoList(id, this.state.userId).then(resp => {
            if (resp.data.success) {
                this.setState({ todoList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
        });
    }

    onRowClick(rowData) {
        this.props.history.push('/todo/' + rowData.id);
    }

  
    render() {
        var data = {
            columns: [
              {
                label: 'Name',
                field: 'name',
                sort: 'asc',
                width: 150
              },
        
            ],
            rows: this.state.todoList
        };
        return (
            <div>
                <Grid

                    container
                    spacing={0}
                    direction="column"
                    alignItems="center"
                    justify="center"
                    style={{minHeight: '100vh' , backgroundImage: `url(${bgImage})` }}
                
                >
                    <Button
                    variant="contained"
                    style={{position: 'absolute',right: 10,top: 0,bottom: 0,  marginLeft:60, width: 150, marginTop: 20, height: 50 }}
                    color="primary"
                    onClick={this.handleLogout}
                    className={this.classes.button}
                    >
                        Logout
                        <Exit>
                        style={{marginLeft:10, marginTop:20, marginRight:20, width: 40, height: 40}}
                        </Exit>
                    </Button>
                    <Card className={this.classes.card}>
                        <CardHeader
                            title="USER TODO LIST"
                        />
                    <CardContent>
                        <div>
                        <MaterialTableDemo
                        style={{ marginLeft: 50, marginRight: 50, marginTop: 50 }}
                        title="User TodoList"
                        dataSet={this.state.todoList}
                        columns={this.state.tableColumns}
                        editable={{
                            onRowAdd: newData =>
                                new Promise(resolve => {
                                    console.log("NEW DATA " + newData.name);
                                    setTimeout(() => {
                                        resolve();
                                        this.createTodoList(newData.name);
                                    }, 600);
                                }),
                            onRowDelete: oldData =>
                                new Promise(resolve => {
                                    setTimeout(() => {
                                        resolve();
                                        this.deleteTodoList(oldData.id);
                                    }, 600);
                                }),
                        }}
                        onRowClick={(event, rowData) => this.onRowClick(rowData)}

                    >

                    </MaterialTableDemo>
                        </div>
                        </CardContent>
                        </Card>
                    </Grid ></div>
        );
    }
}

export default Home;