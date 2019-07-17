
import React from 'react'
import Moment from 'moment'
import purple from '@material-ui/core/colors/purple';
import green from '@material-ui/core/colors/green';
import More from '@material-ui/icons/More';
import Exit from '@material-ui/icons/ExitToApp';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';
import Grid from '@material-ui/core/Grid';
import { Button } from '@material-ui/core';
import bgImage from '../images/bg_image.jpg';  
import MaterialTableDemo from '../functions/materialTable';
import { createMuiTheme } from '@material-ui/core/styles';
import BaseScreen from './baseView';

class TodoList extends BaseScreen {

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
        Moment.locale('tr')
        super(props);
        this.state = {
            todoItemList: [],
            tableColumns: [
                { title: 'Todo List Name', field: 'name' },
                { title: 'Description', field: 'description' },
                {
                    field: 'state', title: 'State', render: rowData => rowData.state === 0 ? "Not Completed" : "Completed", lookup: { 0: 'Not Completed', 1: 'Completed' }
                },
                { title: 'Deadline', field: 'deadline', render: rowData => Moment(rowData.deadline).format('d MMM YYYY HH:mm'), type: 'datetime', customFilterAndSearch: (term, rowData) => Moment(term).isBefore(rowData.deadline) },
            ],
            listId: 0
        };
        this.handleLogout = this.handleLogout.bind(this);
    }


    handleLogout(event) {
        localStorage.clear();
        this.checkSession();
    }
    componentDidMount() {
        console.log(this.props.match.params.id);
        var listId = this.props.match.params.id;
        this.checkSession();
        this.setState({ listId: listId });
        this.getTodoListItems(listId);
    }

    getTodoListItems(listId) {
        this.services.getTodoItems(listId).then(resp => {
            if (resp.data.success) {
                this.setState({ todoItemList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
            console.log(resp.data);
        })
    }

    createTodoItem(name, description, state, deadline, listId) {
        this.services.createTodoItem(name, description, state, deadline, listId, 1).then(resp => {
            if (resp.data.success) {
                this.setState({ todoItemList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
        });
    }

    deleteTodoItem(id, listId) {
        this.services.deleteTodoItem(id, listId).then(resp => {
            if (resp.data.success) {
                this.setState({ todoItemList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
        });
    }

    onRowClick(rowData,listId) {
        this.services.markTodoItem(rowData.id, listId,rowData.state).then(resp => {
            if (resp.data.success) {
                this.setState({ todoItemList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
        });
    }

    render() {
        return (
            <div  >
                <Grid
                    container
                    spacing={0}
                    direction="column"
                    alignItems="center"
                    justify="center"
                    style={{ minHeight: '100vh', backgroundImage: `url(${bgImage})` }}
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
                            title="USER TODO ITEM LIST"
                        />
                    <CardContent>
                        <div>
                <MaterialTableDemo
                    style={{ marginLeft: 50, marginRight: 50, marginTop: 50 }}
                    dataSet={this.state.todoItemList}
                    columns={this.state.tableColumns}
                    options={{
                        filtering: true,
                        selection: false,
                        rowStyle: rowData => ({
                            backgroundColor: (rowData.state === 1) ? '#EEE' : '#FFF',
                        }),

                    }}
                    editable={{
                        onRowAdd: newData =>
                            new Promise(resolve => {
                                console.log("NEW DATA " + newData.name);
                                setTimeout(() => {
                                    resolve();
                                    this.createTodoItem(newData.name, newData.description, newData.state, newData.deadline, this.state.listId);
                                }, 600);
                            }),
                        onRowDelete: oldData =>
                            new Promise(resolve => {
                                setTimeout(() => {
                                    resolve();
                                    this.deleteTodoItem(oldData.id, this.state.listId);
                                }, 600);
                            }),
                    }}
                    actions={[
                        {
                            icon: More,
                            tooltip: 'Dependency Detail',
                            onClick: (event, rowData) =>  this.props.history.push('/dependency/'+rowData.id + "/" + this.state.listId)
                        }
                    ]}

                    onRowClick={(event, rowData, togglePanel) => this.onRowClick(rowData,this.state.listId)}

                >

                </MaterialTableDemo>
                </div>
                </CardContent>
                </Card>
                </Grid>
            </div>
        );
    }
}
export default TodoList;