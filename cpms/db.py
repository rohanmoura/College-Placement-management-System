from flask_sqlalchemy import SQLAlchemy

# Create the SQLAlchemy database instance
# This is kept in a separate file to avoid circular imports
db = SQLAlchemy()
