import React, {Component} from 'react';
import {GiBookshelf, GiBlackBook} from 'react-icons/gi'
import {Link} from '@reach/router';

class Navigation extends Component {
    render() {
        return (
            <nav className="site-nav family-sans navbar navbar-expand bg-primary navbar-dark higher">
                <div className="container-fluid">
                    <a href="/" className="navbar-brand text-uppercase font-weight-bold">
                        Book Library
                    </a>
                    <div className="navbar-nav ml-auto">
                        <Link to="/" className="nav-item nav-link">
                            <GiBookshelf className="mr-1" /> Home
                        </Link>
                        <Link className="nav-item nav-link" to="/editBook">
                            <GiBlackBook className="mr-1" /> Add book
                        </Link>
                    </div>
                </div>
            </nav>
        );
    }
}

export default Navigation;