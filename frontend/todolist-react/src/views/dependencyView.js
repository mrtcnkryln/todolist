
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

class Dependency extends BaseScreen {

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
            dependencyList: [],
            notDependencyList: [],
            userId: this.getUserId(),
            tableColumns: [
                { title: 'Name', field: 'itemTo.name' },
                { title: 'Description', field: 'itemTo.description' },
                {
                    field: 'itemTo.state', title: 'State', render: rowData => rowData.itemTo.state === 0 ? "Not Completed" : "Completed"
                }
            ],
            nonDependencyTableColumns: [
                { title: 'Name', field: 'name' },
                { title: 'Description', field: 'description' },
                {
                    field: 'state', title: 'State', render: rowData => rowData.state === 0 ? "Not Completed" : "Completed", lookup: { 0: 'Not Completed', 1: 'Completed' }
                }
            ]
        };
        this.handleLogout = this.handleLogout.bind(this);
    }


    handleLogout(event) {
        localStorage.clear();
        this.checkSession();
    }
    componentDidMount() {
        var itemId = this.props.match.params.id;
        var listId = this.props.match.params.listId;
        this.checkSession();
        this.setState({ itemId: itemId, listId: listId });
        this.getDependecyList(itemId);
        this.getNonDependecyList(itemId, listId);
    }

    getDependecyList(itemId) {
        this.services.getDependenyItems(itemId).then(resp => {
            if (resp.data.success) {
                this.setState({ dependencyList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
            console.log(resp.data);
        })
    }

    getNonDependecyList(itemId, listId) {
        this.services.getNotDependTodoItems(itemId, listId).then(resp => {
            if (resp.data.success) {
                this.setState({ notDependencyList: resp.data.data });
            } else {
                alert(resp.data.message);
            }
            console.log(resp.data);
        })
    }

    deleteDependencyItem(id) {
        this.services.deleteDependency(id, this.state.itemId).then(resp => {
            if (resp.data.success) {
                this.getDependecyList(this.state.itemId);
                this.getNonDependecyList(this.state.itemId, this.state.listId);
            } else {
                alert(resp.data.message);
            }
        });
    }

    onAddDependencyClick(rowData) {
        this.services.createDependency(this.state.itemId, rowData.id).then(resp => {
            if (resp.data.success) {
                this.getDependecyList(this.state.itemId);
                this.getNonDependecyList(this.state.itemId, this.state.listId);
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
                        style={{ marginTop: 30,  marginBottom: 30 }}
                        title="Depenceny List"
                        dataSet={this.state.dependencyList}
                        columns={this.state.tableColumns}
                        editable={{
                            onRowDelete: oldData =>
                                new Promise(resolve => {
                                    setTimeout(() => {
                                        resolve();
                                        this.deleteDependencyItem(oldData.id);
                                    }, 600);
                                }),
                        }}
                    >
                    </MaterialTableDemo>

                    <MaterialTableDemo
                        style={{ marginBottom: 30  }}
                        title="Add Dependency"
                        dataSet={this.state.notDependencyList}
                        columns={this.state.nonDependencyTableColumns}
                        onRowClick={(event, rowData) => this.onAddDependencyClick(rowData)}
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
export default Dependency;