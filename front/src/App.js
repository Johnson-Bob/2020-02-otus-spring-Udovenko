import React, {Component} from 'react';
import {Router} from '@reach/router';
import 'bootstrap/dist/css/bootstrap.css';
import './index.css';
import Home from './Home'
import EditBook from './EditBook';
import Navigation from './Navigation'



class App extends Component {
  constructor() {
    super();
    this.state = {
      newBook: true,
      update: true,
    }
  }

  render() {
    return (
            <div>
              <Navigation />
              <Router>
                <Home path="/" />
                <EditBook path="/editBook" new={this.state.newBook}/>
              </Router>
              </div>
    );
  }
}

export default App;
