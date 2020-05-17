import React, { Component } from "react";

class Home extends Component {
  render() {
      
    return (
      <div class="container text-centr">
        <div class="row justify-content-center">
          <div class="col-10 col-md-10 col-lg-8 col-xl-7">
            <div class="display-4 text-primary mt-3 mb-2">
              Welcome to Book Library
            </div>
            <a href="/addBook" class="btn btn-outline-primary mr-2">
                    Add Book
            </a>
          </div>
        </div>
      </div>
    );
  }
}

export default Home;