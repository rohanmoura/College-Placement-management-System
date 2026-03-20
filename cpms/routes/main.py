from flask import Blueprint

main_bp = Blueprint("main", __name__)


@main_bp.route("/")
def home():
    """Home route to verify the app is running."""
    return "CPMS Running"
