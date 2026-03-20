from db import db


class Student(db.Model):
    """Student model matching the student table in schema.sql."""

    __tablename__ = "student"

    student_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(100), unique=True, nullable=False)
    contact = db.Column(db.String(15), unique=True, nullable=False)
    course = db.Column(db.String(50), nullable=False)
    year = db.Column(db.String(20), nullable=False)
    skills = db.Column(db.String(255))
    resume = db.Column(db.String(255))
    password = db.Column(db.String(255), nullable=False)
