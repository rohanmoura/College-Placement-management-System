from db import db


class Admin(db.Model):
    """Admin model matching the admin table in schema.sql."""

    __tablename__ = "admin"

    admin_id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    username = db.Column(db.String(50), unique=True, nullable=False)
    password = db.Column(db.String(100), nullable=False)
    role = db.Column(db.String(30), default="Admin")
    email = db.Column(db.String(100), unique=True)
    contact = db.Column(db.String(15), unique=True)
