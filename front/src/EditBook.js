import React, { Component } from "react";

class EditBook extends Component {
    constructor() {
        super();
        this.state = {
            title: '',
            genre: '',
            firstName: '', 
            lastName: '',
            genres: []
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        const itemName = e.target.name;
        const itemValue = e.target.value;

        this.setState({[itemName]: itemValue});       
    }

    handleSubmit(e) {
        const book = {
            bookTitle: this.state.title,
            genre: this.state.genre,
            authors: [{id: '', firstName: this.state.firstName, lastName: this.state.lastName}]
        }

        e.preventDefault();
    }

    async componentDidMount() {
        fetch('/genres')
        .then(response => response.json())
        .then(body => {
            if(body.length != 0) {
                this.setState({genres: body});
            }
        });
    }

    render() {
        const { newBook } = this.props;

        return(
            <form className="mt-3" onSubmit={this.handleSubmit}>
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-lg-8">
                        <div className="card bg-light">
                            <div className="card-body">
                            <h3 className="font-weight-light mb-3">Edit book</h3>
                            <div className="form-row">
                                <section className="col-sm-12 form-group">
                                <label
                                    className="form-control-label"
                                    htmlFor="title"
                                >
                                    Title
                                </label>
                                <input
                                    className="form-control"
                                    type="text"
                                    id="title"
                                    placeholder="Title"
                                    name="title"
                                    required
                                    value={this.state.title}
                                    onChange={this.handleChange}
                                />
                                </section>
                            </div>
                            <section className="form-group">
                                <label
                                    className="form-control-label"
                                    htmlFor="genre"
                                    >
                                    Genre
                                </label>
                                <select
                                    className="form-control"
                                    id="genre"
                                    placeholder="Genre"
                                    required
                                    name="genre"
                                    value={this.state.genre}
                                    onChange={this.handleChange}
                                >
                                    {this.state.genres.map((value, index) => {
                                        return <option key={index} value={value}>{value}</option>
                                    })}
                                </select>
                            </section>
                            <div className="form-row">
                                <section className="col-sm-6 form-group">
                                <label
                                    className="form-control-label"
                                    htmlFor="firstName"
                                >
                                    Author first name
                                </label>
                                <input
                                    id="firstName"
                                    className="form-control"
                                    type="text"
                                    name="firstName"
                                    placeholder="First name"
                                    value={this.state.firstName}
                                    onChange={this.handleChange}
                                />
                                </section>
                                <section className="col-sm-6 form-group">
                                <label
                                    className="form-control-label"
                                    htmlFor="lastName"
                                >
                                    Last name
                                </label>
                                <input
                                    id="lastName"
                                    className="form-control"
                                    type="text"
                                    required
                                    name="lastName"
                                    placeholder="Last name"
                                    value={this.state.lastName}
                                    onChange={this.handleChange}
                                />
                                </section>
                            </div>
                            <div className="form-group text-right mb-0">
                                <button className="btn btn-primary" type="submit">
                                    Save
                                </button>
                            </div>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>
            </form>
        ); 
    }
}

export default EditBook;