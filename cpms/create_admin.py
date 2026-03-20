"""
Create Admin Script
-------------------
Run this script to create an admin user with a hashed password.

Usage:
    python create_admin.py

You will be prompted to enter admin details.
"""

from werkzeug.security import generate_password_hash
from app import create_app
from db import db
from models.admin import Admin


def create_admin():
    app = create_app()

    with app.app_context():
        print("=== Create New Admin ===\n")

        username = input("Enter username: ").strip()
        if not username:
            print("Error: Username cannot be empty.")
            return

        # Check if username already exists
        if Admin.query.filter_by(username=username).first():
            print(f"Error: Admin '{username}' already exists.")
            return

        password = input("Enter password: ").strip()
        if not password:
            print("Error: Password cannot be empty.")
            return

        email = input("Enter email (optional, press Enter to skip): ").strip() or None
        contact = input("Enter contact (optional, press Enter to skip): ").strip() or None

        new_admin = Admin(
            username=username,
            password=generate_password_hash(password),
            role="Admin",
            email=email,
            contact=contact,
        )
        db.session.add(new_admin)
        db.session.commit()

        print(f"\nAdmin '{username}' created successfully!")


if __name__ == "__main__":
    create_admin()
