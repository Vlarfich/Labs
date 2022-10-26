package csdev;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;


/**
 * <p>MessageResult class: Server returns data base class
 * @author Sergey Gutnikov
 * @version 1.0
 */
public abstract class MessageResult extends Message implements Serializable {

	 public static class Data implements Serializable {
			private static final long serialVersionUID = 1L;

			private byte id;

			public byte getID() {
				return id;
			}
			public void setID(byte id) {
				assert( Protocol.validID( id )== true );
				this.id = id;
			}

			private int errCode;

			public int getErrorCode() {
				return errCode;
			}
			public void setErrorCode(int errorCode) {
				this.errCode = errorCode;
			}
			public boolean Error() {
				return errCode != Protocol.RESULT_CODE_OK;
			}

			private String errMessage;

			public String getErrorMessage() {
				return errMessage;
			}
			public void setErrorMessage(String errorMessage) {
				this.errMessage = errorMessage;
			}

			public Data() {
			}

			public String toString() {
				return "" + id + ", " + errCode + ", " + errMessage;
			}

	    }

		private static final long serialVersionUID = 1L;

		protected abstract MessageResult.Data getData();

		public byte getID() {
			return getData().getID();
		}
		public int getErrorCode() {
			return getData().getErrorCode();
		}
		public boolean Error() {
			return getData().Error();
		}

		public String getErrorMessage() {
			return getData().getErrorMessage();
		}

		protected MessageResult() {
		}

		protected void setup( byte id, String errorMessage ) {
			getData().setID(id);
			getData().setErrorCode(Protocol.RESULT_CODE_ERROR);
			getData().setErrorMessage(errorMessage);
		}

		protected void setup( byte id ) {
			getData().setID(id);
			getData().setErrorCode(Protocol.RESULT_CODE_OK);
			getData().setErrorMessage("");
		}

		public String toString() {
			return getData().toString();
		}
}