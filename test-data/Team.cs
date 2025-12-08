using System;
using System.Collections.Generic;

namespace SportsLeague.Data.Models;

public partial class Team
{
    public int TeamId { get; set; }

    public string Name { get; set; } = null!;

    public string City { get; set; } = null!;

    public string? Coach { get; set; }

    public virtual ICollection<Match> MatchAwayTeams { get; set; } = new List<Match>();

    public virtual ICollection<Match> MatchHomeTeams { get; set; } = new List<Match>();

    public virtual ICollection<Player> Players { get; set; } = new List<Player>();
}
