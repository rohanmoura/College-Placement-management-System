from flask import Flask
from config import Config
from db import db
from routes.main import main_bp
from routes.auth import auth_bp
from routes.student import student_bp
from routes.admin import admin_bp


def create_app():
    """Create and configure the Flask application."""
    app = Flask(__name__)
    app.config.from_object(Config)

    # Initialize SQLAlchemy with the app
    db.init_app(app)

    # Register blueprints
    app.register_blueprint(main_bp)
    app.register_blueprint(auth_bp)
    app.register_blueprint(student_bp)
    app.register_blueprint(admin_bp)

    # Import models so db.create_all() can detect them
    import models.student  # noqa: F401
    import models.admin  # noqa: F401
    import models.company  # noqa: F401
    import models.job_posting  # noqa: F401
    import models.job_application  # noqa: F401

    # Create all database tables
    with app.app_context():
        db.create_all()

    return app


if __name__ == "__main__":
    app = create_app()
    app.run(debug=True)
