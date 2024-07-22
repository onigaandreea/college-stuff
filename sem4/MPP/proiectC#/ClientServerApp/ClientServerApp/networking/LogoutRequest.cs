using System;
using model;

namespace network
{
	[Serializable]
    public class LogoutRequest : Request
	{
		private User user;

		public LogoutRequest(User user)
		{
			this.user = user;
		}

		public virtual User User
		{
			get
			{
				return user;
			}
		}
	}

}