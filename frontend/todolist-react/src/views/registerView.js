import React from 'react';
import { createMuiTheme } from '@material-ui/core/styles';
import purple from '@material-ui/core/colors/purple';
import green from '@material-ui/core/colors/green';
import AccountCircle from '@material-ui/icons/AccountCircle';
import Lock from '@material-ui/icons/Lock';
import Info from '@material-ui/icons/Info';
import Card from '@material-ui/core/Card';
import Grid from '@material-ui/core/Grid';
import CardHeader from '@material-ui/core/CardHeader';
import CardContent from '@material-ui/core/CardContent';
import TextField from '@material-ui/core/TextField';
import bgImage from '../images/bg_image.jpg';  
import { Button } from '@material-ui/core';
import BaseScreen from './baseView';


class Register extends BaseScreen {

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
            username: '',
            password: ''
        };

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount() {
      localStorage.clear();
    }
    handleSubmit(event) {
        this.services.register(this.state.username, this.state.password, this.state.name, this.state.surname).then(response => {
            if (response.data.success) {
              localStorage.setItem("user", JSON.stringify(response.data.data[0]));
              this.props.history.push('/home');
            } else {
              alert(response.data.message)
            }
            console.log(response);
          })
            .catch(error => {
              console.log(error);
              alert(error);
            });

    }

    render() {
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
                    <Card className={this.classes.card}>
                        <CardHeader
                            title="Register Form"
                        />

                        <CardContent>
                            <div>
                                <div>
                                    <AccountCircle
                                     style={{ marginTop:20, marginRight:20, width: 40, height: 40}}
                                    ></AccountCircle>
                                    <TextField
                                        id="filled-name"
                                        label="Username"
                                        style={{ width: 400 }}
                                        margin="normal"
                                        variant="outlined"
                                        value={this.state.username}
                                        onChange={(event) => (this.setState({ username: event.target.value }))}
                                    />
                                </div>
                                <div>
                                    <Info
                                     style={{ marginTop:20, marginRight:20, width: 40, height: 40}}
                                    ></Info>
                                    <TextField
                                        id="filled-name"
                                        label="FullName"
                                        style={{ width: 400 }}
                                        margin="normal"
                                        variant="outlined"
                                        value={this.state.fullname}
                                        onChange={(event) => (this.setState({ fullname: event.target.value }))}
                                    />
                                </div>
                                <div>
                                <Lock
                                     style={{ marginTop:20, marginRight:20, width: 40, height: 40}}
                                    ></Lock>
                                    <TextField
                                        id="filled-name"
                                        type="password"
                                        label="Password"
                                        style={{ width: 400 }}
                                        margin="normal"
                                        variant="outlined"
                                        value={this.state.password}
                                        onChange={(event) => (this.setState({ password: event.target.value }))}
                                    />
                                </div>
                                <div>
                                    <Button
                                        variant="contained"
                                        style={{marginLeft:60, width: 400, marginTop: 20, height: 50 }}
                                        color="primary"
                                        onClick={this.handleSubmit}
                                        className={this.classes.button}>
                                        Register
                            </Button>
                            
                                </div>
                            </div>
                        </CardContent>



                    </Card></Grid ></div>
        );
    }
}

export default Register;