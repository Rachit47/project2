import React, { useState } from "react";
import { Form, Button, Container, Row, Col, Alert } from "react-bootstrap";
import { useNavigate } from "react-router";
import FormField from "./FormField";
import { registerUser } from "../services/AuthService";

const SignupForm = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: ""
    });

    const [response, setResponse] = useState({ success: null, message: "" });
    const [isLoading, setIsLoading] = useState(false);
    const [passwordError, setPasswordError] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));

        // Check password match when typing in confirm password
        if (name === "confirmPassword" || (name === "password" && formData.confirmPassword)) {
            if (name === "password" && value !== formData.confirmPassword) {
                setPasswordError("Passwords do not match");
            } else if (name === "confirmPassword" && value !== formData.password) {
                setPasswordError("Passwords do not match");
            } else {
                setPasswordError("");
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validate password match
        if (formData.password !== formData.confirmPassword) {
            setPasswordError("Passwords do not match");
            return;
        }

        setIsLoading(true);

        try {
            await registerUser({
                firstName: formData.firstName,
                lastName: formData.lastName,
                email: formData.email,
                password: formData.password
            });

            setResponse({
                success: true,
                message: "Registration successful! You can now login."
            });

            // Redirect to login page after successful registration
            setTimeout(() => {
                navigate("/login");
            }, 2000);
        } catch (error) {
            setResponse({
                success: false,
                message: error.response?.data?.message || "Registration failed. Please try again."
            });
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <Container className="mt-5">
            {response.success !== null && (
                <Alert variant={response.success ? "success" : "danger"}>
                    {response.message}
                </Alert>
            )}

            <Form onSubmit={handleSubmit}>
                <Row className="mb-3">
                    <Col md={6}>
                        <FormField
                            label="First Name"
                            name="firstName"
                            type="text"
                            value={formData.firstName}
                            onChange={handleChange}
                        />
                    </Col>

                    <Col md={6}>
                        <FormField
                            label="Last Name"
                            name="lastName"
                            type="text"
                            value={formData.lastName}
                            onChange={handleChange}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={12}>
                        <FormField
                            label="Email"
                            name="email"
                            type="email"
                            value={formData.email}
                            onChange={handleChange}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <FormField
                            label="Password"
                            name="password"
                            type="password"
                            value={formData.password}
                            onChange={handleChange}
                        />
                    </Col>

                    <Col md={6}>
                        <FormField
                            label="Confirm Password"
                            name="confirmPassword"
                            type="password"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                        />
                        {passwordError && (
                            <div className="text-danger mt-1">{passwordError}</div>
                        )}
                    </Col>
                </Row>

                <Button
                    variant="primary"
                    type="submit"
                    className="mt-3"
                    disabled={isLoading || passwordError}
                >
                    {isLoading ? "Signing up..." : "Sign Up"}
                </Button>

                <div className="mt-3">
                    Already have an account? <a href="/login">Login here</a>
                </div>
            </Form>
        </Container>
    );
};

export default SignupForm;
